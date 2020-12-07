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



    @Test
    public void TC1_findByIdのテスト_idが1のデータが取得できること() {

    	// STEP1(given): 期待値を設定する
    	BulletinBoardDto expected = new BulletinBoardDto();
    	expected.setId(1);
    	expected.setCreateDate(LocalDate.of(2020, 11, 9));
    	expected.setTitle("aaaaaaa");
    	expected.setContent("11");
    	expected.setCreateUser("User1");
    	expected.setDeleteFlg(false);
    	expected.setUpdateDate(LocalDate.of(2020, 11, 9));

        // STEP2(when): 試験実施
    	BulletinBoardDto actual = dao.findById(1);

        //　STEP3(then): 期待値のチェック
        assertThat(actual.getId(),         is(expected.getId()));
        assertThat(actual.getCreateDate(), is(expected.getCreateDate()));
        assertThat(actual.getTitle(),      is(expected.getTitle()));
        assertThat(actual.getContent(),    is(expected.getContent()));
        assertThat(actual.getCreateUser(), is(expected.getCreateUser()));
        assertThat(actual.isDeleteFlg(),   is(expected.isDeleteFlg()));
        assertThat(actual.getUpdateDate(), is(expected.getUpdateDate()));

    }

    @Test
    public void TC2_findByDeleteFlgのテスト_deleteFlgがfalseのデータが取得できること() {

    	// STEP1(given): 期待値を設定する
    	BulletinBoardDto expected = new BulletinBoardDto();
    	expected.setId(1);

        // STEP2(when): 試験実施
    	List<BulletinBoardDto> actual = dao.findByDeleteFlg(false);

        //　STEP3(then): 期待値のチェック
    	assertThat(actual.get(0).getId(), is(expected.getId()));

    }

    @Test
    public void TC3_findByDeleteFlgのテスト_deleteFlgがtrueのデータが取得できること() {

    	// STEP1(given): 期待値を設定する
    	BulletinBoardDto expected = new BulletinBoardDto();
    	expected.setId(2);

        // STEP2(when): 試験実施
    	List<BulletinBoardDto> actual = dao.findByDeleteFlg(true);

        //　STEP3(then): 期待値のチェック
    	assertThat(actual.get(0).getId(), is(expected.getId()));

    }

    @Test
    public void TC4_updateDeleteFlgのテスト_deleteFlgをtrueに更新できること() {

    	// STEP1(given): 期待値を設定する
    	BulletinBoardDto expected = new BulletinBoardDto();
    	expected.setDeleteFlg(true);

        // STEP2(when): 試験実施
    	dao.updateDeleteFlg(1, true);

    	//更新後のデータを取得
    	BulletinBoardDto actual = dao.findById(1);

        //　STEP3(then): 期待値のチェック
        assertThat(actual.isDeleteFlg(), is(expected.isDeleteFlg()));

    }


}
