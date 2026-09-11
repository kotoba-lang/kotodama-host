(ns kotodama.host-sdk.host-contract
  "Generated CLJC facade artifact for @etzhayyim/kotoba-kotodama-host-sdk.

  Operation shapes are owned by kotodama.host EDN/CLJC authority."
  (:require [clojure.set :as set]))

(def facade
  {:kotodama.host.sdk/package "@etzhayyim/kotoba-kotodama-host-sdk"
   :kotodama.host.sdk/authority [:kotoba-clj :edn]
   :kotodama.host.sdk/facade :cljc-generated
   :kotodama.host.sdk/generated "src/host-contract.cljc"
   :kotodama.host.sdk/runtime-entry "src/host-contract.cljc"
   :kotodama.host.sdk/operations
   [{:kotodama.host.sdk/name :create-host-sdk
     :kotodama.host.sdk/request :actor/register-request
     :kotodama.host.sdk/response :actor/register-response
     :kotodama.host.sdk/component-export :actor/register}
    {:kotodama.host.sdk/name :dispatch
     :kotodama.host.sdk/request :actor/dispatch-request
     :kotodama.host.sdk/response :actor/dispatch-response
     :kotodama.host.sdk/component-export :actor/dispatch}
    {:kotodama.host.sdk/name :cancel
     :kotodama.host.sdk/request :actor/cancel-request
     :kotodama.host.sdk/response :actor/cancel-response
     :kotodama.host.sdk/component-export :actor/cancel}
    {:kotodama.host.sdk/name :health
     :kotodama.host.sdk/request :host/health-request
     :kotodama.host.sdk/response :host/health-response
     :kotodama.host.sdk/component-export :host/health}]})

(def operations
  (:kotodama.host.sdk/operations facade))

(def operations-by-name
  (into {} (map (juxt :kotodama.host.sdk/name identity) operations)))

(defn operation [name]
  (get operations-by-name name))

(defn component-exports []
  (set (map :kotodama.host.sdk/component-export operations)))

(defn covers-exports? [exports]
  (set/subset? (component-exports) (set exports)))
