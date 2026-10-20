# CLJC facade 移行プログラム（JUN-133 完了条件の中核）

TS SDK（`sdk/kotodama-host-sdk`）は compat として復元済み（RESTORATION.md）。
撤去条件は「**appview 8 本が CLJC facade へ移行**」。本文書はその移行の
設計と順序。実測は 2026-10-20 時点。

## 現状（実測）

- **EDN 権威はある**: `resources/kotodama_host/sdk_facade.edn` —
  `:facade :cljc-generated`、`:generated "src/host-contract.cljc"`、
  operations（:create-host-sdk / :dispatch / ...）を宣言。
- **生成パイプラインが無い**: scripts/ に生成器は存在せず、宣言どおりの
  `src/host-contract.cljc` も無い。あるのは `src/kotodama/host_contract.cljk`
  = **EDN 権威ローダー**（boundary / provider-catalog / sdk-facade /
  sdk-sources / config-schema を読み host-exports を定義するだけ）。
  edn の `:generated` パスと実体が食い違っている（要訂正）。
- **消費者**: TS SDK の createWorkerExport / dispatch 等を import する
  appview の `app.ts` が漂流（RESTORATION.md: app-itonami / app-editor /
  app-kenkyusha / app-playwright / app-briefing / llm / repository /
  os-messaging の 8 本が bundle 不能だった = 外観は復元で解消）。実測では
  消費者は 8 本に留まらず（defence-handlers.ts 系や他 appview の app.ts）。

## 移行の設計

```
sdk_facade.edn（権威）
  │  生成器（新設: kbb スクリプト、zero-dep）
  ▼
src/kotodama/host_facade.cljc（生成物: operations を公開する実行可能 facade）
  │  kbb / amu で JS へ
  ▼
appview の app.ts は TS SDK の代わりに生成 facade を import
```

- facade の API 面は TS SDK の互換（create-host-sdk / dispatch / ...）を
  保ち、appview 側の差分は import だけに留めるのが目標。
- 生成器は EDN 1 本から出力を決める（単一権威 = 5b2b084 の「EDN/CLJC
  authority」方針）。生成物ヘッダに provenance を残す（lexicon codegen と同じ
  流儀）。

## スライス順序（1 run = 1 スライス）

1. **生成器**: `scripts/gen-sdk-facade.cljk`（sdk_facade.edn →
   host_facade.cljc）+ ゴールデンテスト。edn の `:generated` パスを実体に
   訂正。
2. **facade の実行検証**: host_facade.cljc を kbb で動かし TS SDK の
   ゴールデン挙動と一致を取る（parity）。
3〜10. **appview 8 本を 1 本ずつ** import 差替え + bundle 検証
   （app-itonami → app-editor → app-kenkyusha → app-playwright →
   app-briefing → llm → repository → os-messaging）。
11. **TS SDK 再削除**（RESTORATION.md の撤去条件充足を宣言し削除）。

## 未確定事項（要判断）

- 生成 facade の実行環境（kbb sci / amu js target / 両方）。
- defence-handlers.ts 系の消費者（8 本外）を SDK 撤去前にどこまで移行するか。
