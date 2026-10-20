;; kotodama.host-facade — GENERATED FILE. DO NOT EDIT.
;; 生成器: scripts/gen-sdk-facade.cljk
;; 権威: resources/kotodama_host/sdk_facade.edn
;; 生成時刻は provenance に含めない（決定的な出力にするため）。

(ns kotodama.host-facade
  "kotodama.host SDK facade（生成物）。operations の単一権威は
  resources/kotodama_host/sdk_facade.edn。ここはその生成物。"
  (:require [clojure.string :as str]))

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
