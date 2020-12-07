package com.example.demo;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BulletinBoardController {

    @Autowired
    BulletinBoardDao bulletinBoardDao;

    @Autowired
    HttpSession session;

    /**
     * 一覧を表示（トップページ）
     */
    @RequestMapping(value = "/")
    public String index(Model model) {

    	//DBから情報を取得しDtoに格納
        List<BulletinBoardDto> list = bulletinBoardDao.findByDeleteFlg(false);
        model.addAttribute("list", list);

        //トップページに遷移する
        return "list";
    }



    /**
     * 詳細画面の表示
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String todoDetail(@RequestParam("id") int id, Model model) {

    	//DBからキーを元に情報を取得しDtoに格納
    	BulletinBoardDto dto = bulletinBoardDao.findById(id);
        model.addAttribute("formModel", dto);

        //詳細画面に遷移する
        return "show";
    }


    /**
     * 新規画面への遷移
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {

    	//空のDtoを渡す
    	model.addAttribute("formModel", new BulletinBoardDto());

    	//編集画面に遷移する
        return "edit";
    }


    /**
     * 編集画面の表示
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@RequestParam("id") int id, Model model) {

    	//DBからキーを元に情報を取得しDtoに格納
    	BulletinBoardDto dto = bulletinBoardDao.findById(id);
        model.addAttribute("formModel", dto);

        //編集画面に遷移する
        return "edit";
    }


    /**
     *  更新処理
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("formModel") BulletinBoardDto formModel, Model model) {

    	//入力チェック
    	if(formModel.getTitle().isEmpty()) {
            model.addAttribute("errorMessage", "タイトルは必須項目です");
            return "/edit";
    	}

    	//更新日を格納
    	if(formModel.getId() == 0) {
    		formModel.setCreateDate(LocalDate.now());
    	} else {
        	//取得
        	BulletinBoardDto tmp = bulletinBoardDao.findById(formModel.getId());
        	formModel.setCreateDate(tmp.getCreateDate());
    	}
		formModel.setUpdateDate(LocalDate.now());


    	//DB更新
    	BulletinBoardDto ret = bulletinBoardDao.save(formModel);

    	//メッセージを追加
        model.addAttribute("message", "id:" + ret.getId() + " 「" + ret.getTitle() + "」 を登録しました");

    	//トップページに遷移する
    	return "forward:/";
	}


    /**
     *  論理削除処理
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String updateDeleteFlg(@RequestParam("id") int id, Model model) {

    	//DB更新
    	bulletinBoardDao.updateDeleteFlg(id, true);

    	//メッセージを追加
        model.addAttribute("message", "id:" + id + " を削除しました");

    	//トップページに遷移する
    	return "forward:/";
	}


    /**
     * 分岐表示
     */
    @RequestMapping(value = "/bunkiSelect")
    public String bunkiSelect(Model model) {

    	//トップページに遷移する
    	return "bunkiSelect";
    }



    /**
     * 分岐表示
     */
    @RequestMapping(value = "/bunki")
    public String bunki(@RequestParam("flg") boolean flg, Model model) {

    	session.setAttribute("flg", flg);

    	//トップページに遷移する
    	return "forward:/";
    }

}