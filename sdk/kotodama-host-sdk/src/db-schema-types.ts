// src/db-schema-types.ts — JUN-133 item 1: graph-schema 型参照の local 化。
//
// `@kotoba-lang/graph-schema` の型インポート（StrictDatabase / Database）は
// DB 経路（kysely/pg）と運命を共にする。ADR-2605111200 で Workers の DB 接続
// は禁止され、DB I/O は AgentGateway MCP → pod-side LangServer に移行済み。
// TS SDK の最終撤去（RESTORATION.md の撤去条件）まで、外部型パッケージへの
// 依存を切るための**最小の構造宣言**をここに置く。
//
// これは graph-schema の実体の置き換えではない。テーブル列の厳密な型は
// graph-schema の正本であり、ここでは Kysely が要求する表の形（列名 → 値）
// と、vertex_/edge_ 接頭辞のキー空間だけを再現する。

/**
 * テーブル 1 行の最小形: 列名 → 値。値は移行用に緩める（any）。
 * 厳密な列型は graph-schema の正本であり、TS SDK 撤去までは呼び出し側の
 * 型アノテーションで担保する。
 */
export interface DBRow {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  [column: string]: any;
}

/** vertex_/edge_ 接頭辞の表だけを持つ厳密な DB 形（private-write の制約）。 */
export interface StrictDatabase {
  [table: `vertex_${string}` | `edge_${string}`]: DBRow;
}

/** より広い DB 形（write-outbox が使う）。 */
export interface Database {
  [table: string]: DBRow;
}
