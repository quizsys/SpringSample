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

    /* ここから下がテストクラス */

    @Test
    public void test() throws Exception {

        this.mockMvc.perform(get("/init")) // @GetMapping("/item/{itemId}")のメソッドの実行と結果確認
                .andExpect(status().isOk()); // 以下、結果確認

    }

    @Test
    public void test2() throws Exception {

        when(bulletinBoardDao.findByDeleteFlg(true)).thenReturn(findByDeleteFlgTrue()); // itemService.findByItemId(1)実行時にgetItemIdOfItemId1()の結果が返ることを定義
        this.mockMvc.perform(get("/")) // @GetMapping("/item/{itemId}")のメソッドの実行と結果確認
                // HTTP ステータスコードをテスト
                .andExpect(status().isOk())
                // ビュー名をテスト
                .andExpect(view().name("list"))
                // モデルの属性をテスト
                .andExpect(model().attribute("list", findByDeleteFlgTrue()));

    }


  /*  @Test
    public void test3() throws Exception {

    	BulletinBoardDto dto = findById1();

        when(bulletinBoardDao.findById(1)).thenReturn(dto); // itemService.findByItemId(1)実行時にgetItemIdOfItemId1()の結果が返ることを定義
        this.mockMvc.perform(get("/show1").param("id", "1")) // @GetMapping("/item/{itemId}")のメソッドの実行と結果確認
                // HTTP ステータスコードをテスト
                .andExpect(status().isOk())
                // ビュー名をテスト
                .andExpect(view().name("show"))
                // モデルの属性をテスト
                .andExpect(model().attribute("formModel", dto));
    }
*/
    @Test
    public void test4() throws Exception {

    	BulletinBoardDto dto = findById1();

        when(bulletinBoardDao.save(any(BulletinBoardDto.class))).thenReturn(dto); // itemService.findByItemId(1)実行時にgetItemIdOfItemId1()の結果が返ることを定義
        when(bulletinBoardDao.findById(1)).thenReturn(dto); // itemService.findByItemId(1)実行時にgetItemIdOfItemId1()の結果が返ることを定義
        this.mockMvc.perform(post("/save").param("id", "1").param("title", "title").param("contents", "contents")) // @GetMapping("/item/{itemId}")のメソッドの実行と結果確認
                // HTTP ステータスコードをテスト
                .andExpect(status().isOk())
                // ビュー名をテスト
                .andExpect(view().name("forward:/"));
                // モデルの属性をテスト
//                .andExpect(model().attribute("formModel", dto));
    }

    /* ここから下が試験用のDaoの動作定義 */

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
