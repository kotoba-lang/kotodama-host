// ADR-2605111200 strict guard (JUN-133: transition window 撤去).
// CF Worker 環境では createKyselyDb が WorkerDBProhibitedError で拒否される。
// warn-once のソフトニングに戻らないことを固定する。
import { describe, expect, it, vi } from "vitest";

describe("createKyselyDb CF Worker guard (ADR-2605111200)", () => {
  it("throws WorkerDBProhibitedError inside a CF Worker runtime", async () => {
    const g = globalThis as Record<string, unknown>;
    const hadCaches = "caches" in g;
    const hadScope = "WorkerGlobalScope" in g;
    g.caches = {};
    g.WorkerGlobalScope = class {};
    const warnSpy = vi.spyOn(console, "warn").mockImplementation(() => {});
    try {
      const { createKyselyDb, WorkerDBProhibitedError } = await import("../src/kysely.js");
      expect(() => createKyselyDb()).toThrow(WorkerDBProhibitedError);
      expect(warnSpy).not.toHaveBeenCalled();
    } finally {
      warnSpy.mockRestore();
      if (!hadCaches) delete g.caches;
      if (!hadScope) delete g.WorkerGlobalScope;
    }
  });
});
