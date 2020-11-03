package com.example.demo;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity                  /* エンティティクラス */
@Table(name="TB_Board")  /* テーブル名を定義する */
public class BulletinBoardDto {

	/*
	 * @idは主キーに付与する。
	 * これにより、findById等で検索可能になるほか、saveメソッドで登録・更新を使い分けられる
	 * @GeneratedValueは自動採番の仕組みを定義する。
	 * GenerationType.IDENTITYは、テーブルのidentity列を利用して，主キー値を生成する。
	 */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private LocalDate updateDate;

	private String title;

	private String content;

	private String createUser;

	private boolean deleteFlg;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(LocalDate updateDate) {
		this.updateDate = updateDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public boolean isDeleteFlg() {
		return deleteFlg;
	}
	public void setDeleteFlg(boolean deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

}
