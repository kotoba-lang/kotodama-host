# kotoba-lang/kotodama-host

Host runtimes and SDK surface for Kotodama.

This repository owns:

- TypeScript host SDK used by Kotodama-capable runtimes
- host-facing contracts and browser/runtime integration surfaces that are not
  owned by the portable inference layer

It does not own the portable inference runtime. Inference lives in
`kotoba-lang/inference`.

## Rust Status

The former Rust desktop/KAMI host scaffolds and shared Rust host config have been
removed from this repository. Native hosts should live in adapter repositories
and consume Kotoba/Kotodama contracts through the SDK surface.

The default path should not contain `Cargo.toml`, `Cargo.lock`, `.rs`, or Rust
toolchain files.
