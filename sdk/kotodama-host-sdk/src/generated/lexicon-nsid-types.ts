// src/generated/lexicon-nsid-types.ts — COMPAT STUB（2026-10-19）。
//
// このファイルは旧 monorepo の lexicon codegen（00-contracts/lexicons → 型付き
// NSID 表）の産物だったが、generator は split に取り残されていない。appview の
// bundle 経路が必要とするのは実行時の nsid()（identity）と LEXICON_NSID 参照
// だけであり、型は bundle で消える。よって最小の compat stub を置く。
//
// 撤去条件: lexicon codegen を kotodama-host に移行し、この stub を本物の
// 生成物に差し替える。test/lexicon-nsid-types.test.ts の契約（400+ エントリ、
// 型付き LexiconInput/Output）は現状この stub では満たさない — 名前付きの
// 未達であり、通っているように見せない。

export function nsid<T extends string>(value: T): T {
  return value;
}

// identity record: 任意のキーが自分自身を返す（実行時の参照形だけ保証する）
export const LEXICON_NSID: Record<string, string> = new Proxy(
  {} as Record<string, string>,
  {
    get: (_t, k) => (typeof k === "string" ? k : undefined),
    has: () => true,
  },
);

export const LEXICON_INPUT_SCHEMA: Record<string, unknown> = {};

export type KnownLexiconProcedureNSID = string;
export type KnownLexiconQueryNSID = string;
export type LexiconInput<T extends string = string> = Record<string, unknown>;
export type LexiconOutput<T extends string = string> = Record<string, unknown>;
