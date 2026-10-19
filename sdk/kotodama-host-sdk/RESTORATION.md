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

### 判定（実測 2026-10-19）

**kotoba / amu selfhost（kototama-browser-host、browser-host.mjs）への参照は
ゼロ**（kotoba/src・amu/src・amu/runtime・kototama を検索して 0 件）。この
TS SDK は CF-worker appview 用の compat 遺物であり、selfhost の部品ではない。
よって新世界に残す価値があるのは appview 8 本を bundle するのに必要な最小限
だけで、以下の方針で rename / 除去した:

| 旧 | 処置 | 根拠 |
|---|---|---|
| `@etzhayyim/kami-engine-sdk` | **除去** | src 参照 0（実測） |
| `@etzhayyim/{sdk,did-etzhayyim,xrpc,graph-schema}` | **rename**: `@kotoba-lang/*` へ（leaf 名は `did:etzhayyim` 等の wire 識別子に由来するため不変。import 文字列14 ファイルを更新） | org は既に kotoba-lang へ再配置済み。npm scope が移行に取り残されていた |
| 自パッケージ名 | `@etzhayyim/kotoba-kotodama-host-sdk` → `@kotoba-lang/kotoba-kotodama-host-sdk` | 同上 |
| `kysely` / `pg` / `graph-schema` の実体 | **次の除去 slice**（JUN-131） | Kysely 経路は ADR-2605111200 で Workers 禁止（no-op 方向）。graph-schema はこの経路専用の型（home は新世界に無い）。14 ファイルに波及するため別 slice |
| wire 識別子（`did:etzhayyim` / `com.etzhayyim.*` / NSID） | **不変** | プロトコル。rename の対象外 |

配線（`workspace:*` → `file:` リンク）は rename 済みの3件（sdk / did-etzhayyim /
xrpc、いずれも `kotoba-lang/` 配下で name 確定）について次 slice で適用する。
**それまで `npm install` は失敗する（実測: `Unsupported URL Type "workspace:"`）**。

## 撤去条件

**appview 8 本が CLJC facade（host-contract.cljk 由来の API）へ移行した時点で
再び削除する。** そのときこの復元を「移行が止まった期間の compat」として
git 履歴に残す。新しい consumer にこの TS SDK を増やさない（移行先は facade）。
