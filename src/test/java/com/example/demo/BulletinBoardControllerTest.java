package com.example.demo;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BulletinBoardControllerTest {

	//モックの使用を宣言
    public MockMvc mockMvc;

    @Mock // モックオブジェクトとして使用することを宣言（Daoの名前を差し替える）
    private BulletinBoardDao bulletinBoardDao;

	// テスト対象のコントローラー（Controller名を差し替える）
    @InjectMocks
    public BulletinBoardController controller;

    @SuppressWarnings("deprecation")
	@BeforeEach // テストメソッド（@Testをつけたメソッド）実行前に都度実施（変更しない）
    public void init() {
        MockitoAnnotations.initMocks(this); // アノテーションの有効化
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build(); // MockMvcのセットアップ
    }


    /********* ここから下がテストケース ************************/

    @Test
    public void TC1_トップページアクセス時にlistが返却されること() throws Exception {

    	// STEP1(given): 条件を設定する
    	// ダミーのlistを作成
    	List<BulletinBoardDto> list = findByDeleteFlgTrue();
    	// bulletinBoardDao.findByDeleteFlg(true)実行時にダミーの結果が返ることを定義
        when(bulletinBoardDao.findByDeleteFlg(true)).thenReturn(list);

        // STEP2(when): 試験実施
        this.mockMvc.perform(
        	    // @RequestMapping("/")のメソッドの実行
        			get("/")
        		)

        //　STEP3(then): 試験結果確認
                // HTTP ステータスコードをテスト
                .andExpect(status().isOk())
                // ビュー名をテスト
                .andExpect(view().name("list"))
                // モデルの属性をテスト
                .andExpect(model().attribute("list", list));

    }

    @Test
    public void TC2_更新処理が正常に実施されること() throws Exception {

    	// STEP1(given): 条件を設定する
    	// ダミーのdtoを作成
    	BulletinBoardDto dto = findById1();
    	// bulletinBoardDao.save()実行時にダミーの結果が返ることを定義
        when(bulletinBoardDao.save(any(BulletinBoardDto.class))).thenReturn(dto);
        // bulletinBoardDao.findById(1)実行時にダミーの結果が返ることを定義
        when(bulletinBoardDao.findById(1)).thenReturn(dto);

        // STEP2(when): 試験実施
        this.mockMvc.perform(
        	    	// @RequestMapping("/save")のメソッドの実行
	        		post("/save")
	        		// リクエストパラメータを設定
	        		.param("id", "1")
	        		.param("title", "title")
	        		.param("contents", "contents")
        		)

        //　STEP3(then): 試験結果確認
                // HTTP ステータスコードをテスト
                .andExpect(status().isOk())
                // ビュー名をテスト
                .andExpect(view().name("forward:/"))
                // モデルの属性をテスト
                .andExpect(model().attribute("message", "id:1 「title」 を登録しました"));
    }


    @Test
    public void TC3_タイトル未入力時に入力必須項目のエラーが出て編集画面に遷移すること() throws Exception {

    	// STEP1(given): 条件を設定する
    	// なし

        // STEP2(when): 試験実施
        this.mockMvc.perform(
        	    	// @RequestMapping("/save")のメソッドの実行
	        		post("/save")
	        		// リクエストパラメータを設定
	        		.param("id", "1")
	        		.param("title", "")  //titleを空文字で設定
        		)

        //　STEP3(then): 試験結果確認
                // HTTP ステータスコードをテスト
                .andExpect(status().isOk())
                // ビュー名をテスト
                .andExpect(view().name("/edit"))
                // モデルの属性をテスト
                .andExpect(model().attribute("errorMessage", "タイトルは必須項目です"));
    }



    /********* ここから下が試験用のDaoの動作定義 ************************/

    //daoの差し替えメソッドを定義
	private List<BulletinBoardDto> findByDeleteFlgTrue() {

		List<BulletinBoardDto> list = new ArrayList<>();

		BulletinBoardDto dto = new BulletinBoardDto();
		dto.setId(1);
		dto.setTitle("title");
		dto.setContent("contents");
		dto.setCreateDate(LocalDate.now());

		return list;
	}


    //daoの差し替えメソッドを定義
	private BulletinBoardDto findById1() {

		BulletinBoardDto dto = new BulletinBoardDto();
		dto.setId(1);
		dto.setTitle("title");
		dto.setContent("contents");
//		dto.setCreateDate(LocalDate.now());

		return dto;
	}


}
