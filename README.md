# SpringSample
a
### 構成
```
SpringSample
├─build.gradle ★ビルドに必要な情報を記載
│
└─src
    ├─main
    │  ├─java
    │  │  └─com
    │  │      └─example
    │  │          └─demo
    │  │                  ServletInitializer.java
    │  │                  SpringSampleApplication.java
    │  │                  BulletinBoardController.java    ★コントローラー
    │  │                  BulletinBoardDao.java           ★DAO（モデル）
    │  │                  BulletinBoardDto.java           ★DTO（DBとの値のやり取りをする）
    │  │
    │  └─resources
    │      │  application.properties   ★DBの接続情報を記載
    │      │
    │      └─templates
    │              edit.html  ★編集ページ
    │              list.html  ★一覧ページ
    │              show.html  ★詳細ページ
    │
    └─test
 ```
