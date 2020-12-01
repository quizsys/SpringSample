package com.example.demo;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest //テストでSpringBootの機能を使用する
@Transactional  //テスト実行後に更新データを元に戻す
public class BulletinBoardDaoTest {

    @Autowired
    BulletinBoardDao dao;  //テスト対象のDao ★差し替える



    //期待値： id=1のデータが取得できること & 全項目が期待値通りであること
    @Test
    public void test_findById_1() {

    	//データ取得
    	BulletinBoardDto actual = dao.findById(1);

    	//期待値のチェック
        assertThat(actual.getId(), is(1));
        assertThat(actual.getCreateDate(), is(LocalDate.of(2020, 11, 9)));
        assertThat(actual.getTitle(), is("aaaaaaa"));
        assertThat(actual.getContent(), is("11"));
        assertThat(actual.getCreateUser(), is("User1"));
        assertThat(actual.isDeleteFlg(), is(false));
        assertThat(actual.getUpdateDate(), is(LocalDate.of(2020, 11, 9)));

    }

    //期待値： deleteFlg=falseのデータが取得できること
    @Test
    public void test_findByDeleteFlg_false() {

    	//データ取得
    	List<BulletinBoardDto> actual = dao.findByDeleteFlg(false);

    	//期待値のチェック
    	assertThat(actual.get(0).getId(), is(1));
        assertThat(actual.get(0).getCreateUser(), is("User1"));
    }

    //期待値： deleteFlg=trueのデータが取得できること
    @Test
    public void test_findByDeleteFlg_true() {

    	//データ取得
    	List<BulletinBoardDto> actual = dao.findByDeleteFlg(true);

    	//期待値のチェック
    	assertThat(actual.get(0).getId(), is(2));
        assertThat(actual.get(0).getCreateUser(), is("User2"));


    }

    //期待値： deleteFlgを更新できること
    @Test
    public void test_updateDeleteFlg_1_true() {

    	//データ更新
    	dao.updateDeleteFlg(1, true);

    	//更新後のデータを取得
    	BulletinBoardDto actual = dao.findById(1);

    	//期待値のチェック
        assertThat(actual.isDeleteFlg(), is(true));

    }




}
