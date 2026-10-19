# RESTORATION — TS SDK を履歴から復元した理由と撤去条件

**復元日**: 2026-10-19 / **出所**: commit `5b2b084^`（削除直前）の
`sdk/kotodama-host-sdk/` 全 108 ファイルを `git checkout 5b2b084^ -- sdk/kotodama-host-sdk`
で verbatim 復元。中身は一切変えていない。

## なぜ復元したか

`5b2b084`（"cleanup: land stalled CLJC migration WIP (host contract EDN/CLJC
authority, delete TS SDK)"）は CLJC 移行の WIP を残して TS SDK を削除したが、
**消費者側の移行は止まったまま**だった。`createWorkerExport` 等を import する
appview の `app.ts` が 8 本漂流し（app-itonami / app-editor / app-kenkyusha /
app-playwright / app-briefing / llm / repository / os-messaging — 実測
2026-10-19）、どれも bundle できない。削除は移行の完了より先に走った。

復元は compat であり、CLJC facade（`src/host-contract.cljk`）への移行方針を
変えるものではない。Kysely 経路は ADR-2605111200 のとおり no-op のまま
（Worker-direct DB 接続は禁止。実装側に CHARTER-VIOLATION 注記が残る）。

## 使い方（consumer / CI）

bundle で hono 等の依存を解決できるように、**このディレクトリで
`npm install` を先に実行する**（node_modules は gitignore のまま）。

## 復元後に残る作業: workspace 依存の配線（実測 2026-10-19）

この package.json の `workspace:*` は旧 monorepo の pnpm workspace を前提とし、
split 後は解決できない。依存 5 件の現世の候補は:

| 依存 | 候補（west シブリング） | 状態 |
|---|---|---|
| `@etzhayyim/sdk` | `kotoba-lang/sdk`（name 一致確認済み） | 配線可 |
| `@etzhayyim/did-etzhayyim` | `kotoba-lang/did-etzhayyim`（同） | 配線可 |
| `@etzhayyim/xrpc` | `kotoba-lang/xrpc`（同） | 配線可 |
| `@etzhayyim/graph-schema` | — | **home 未確認（要調査）** |
| `@etzhayyim/kami-engine-sdk` | `kotoba-lang/kami-engine-app-sdk` 候補 | **name 未確認** |

配線方針: `workspace:*` → `file:../../../<repo>` への差し替え（この復元からの
唯一の既定変更として package.json に書く）。全 5 件の home 確定が済んだら
適用する。**それまで `npm install` / `pnpm install` は失敗する（実測:
`Unsupported URL Type "workspace:"`）** — これは復元の欠陥ではなく、
split と CLJC 移行が両方未完了であることの測定値。

## 撤去条件

**appview 8 本が CLJC facade（host-contract.cljk 由来の API）へ移行した時点で
再び削除する。** そのときこの復元を「移行が止まった期間の compat」として
git 履歴に残す。新しい consumer にこの TS SDK を増やさない（移行先は facade）。
