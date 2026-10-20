;; kotodama.host-facade — GENERATED FILE. DO NOT EDIT.
;; 生成器: scripts/gen-sdk-facade.cljk
;; 権威: resources/kotodama_host/sdk_facade.edn
;; 生成時刻は provenance に含めない（決定的な出力にするため）。

(ns kotodama.host-facade
  "kotodama.host SDK facade（生成物）。operations の単一権威は
  resources/kotodama_host/sdk_facade.edn。ここはその生成物。
  appview 差替え面のユーティリティもここで公開する。"
  (:refer-clojure :exclude [str])
  (:require [kotodama.host-util :as host-util]
            [kotodama.host-export :as host-export]
            [kotodama.host-lexicon :as host-lexicon]
            [kotodama.host-heartbeat :as host-heartbeat]))

;; appview 差替え面（docs/appview-replacement-surface.md）
(def str host-util/str')
(def now-iso host-util/now-iso)
(def nsid host-util/nsid)
(def gen-id host-util/gen-id)
(def decode-json host-util/decode-json)

;; Worker/export 系（appview の createWorkerExport / asAgentTool /
;; withCapabilityTags 対応の純粋な登録 API）
(def as-agent-tool host-export/as-agent-tool)
(def with-capability-tags host-export/with-capability-tags)
(def create-worker-export host-export/create-worker-export)
(def command host-export/command)
(def empty-registry host-export/empty-registry)

;; lexicon / heartbeat 系（appview の parseLexiconInput /
;; createInboxBuffer / createCadenceState 対応）
(def parse-lexicon-input host-lexicon/parse-lexicon-input)
(def try-parse-lexicon-input host-lexicon/try-parse-lexicon-input)
(def validate-against-schema host-lexicon/validate-against-schema)
(def create-inbox-buffer host-heartbeat/create-inbox-buffer)
(def create-cadence-state host-heartbeat/create-cadence-state)

(def sdk-package
  "パッケージ名（EDN 権威より）。"
  "@etzhayyim/kotoba-kotodama-host-sdk")

(def sdk-authority
  "権威の形式。"
  [:kotoba-clj :edn])

(def sdk-operations
  "公開 operations: name/request/response/component-export。"
  [
  {:name :create-host-sdk :request :actor/register-request :response :actor/register-response :component-export :actor/register}
  {:name :dispatch :request :actor/dispatch-request :response :actor/dispatch-response :component-export :actor/dispatch}
  {:name :cancel :request :actor/cancel-request :response :actor/cancel-response :component-export :actor/cancel}
  {:name :health :request :host/health-request :response :host/health-response :component-export :host/health}])

(def operation-names
  (mapv :name sdk-operations))

(defn find-operation
  "operation 名（キーワード）から宣言を引く。無ければ nil。"
  [op-name]
  (some (fn [op] (when (= op-name (:name op)) op)) sdk-operations))

(defn operation?
  [op-name]
  (some? (find-operation op-name)))
