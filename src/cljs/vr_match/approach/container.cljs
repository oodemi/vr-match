(ns vr-match.approach.container
  (:require [reagent.core :as reagent]
            [vr-match.approach.component :as component]
            [vr-match.util :as util]
            [re-frame.core :as re-frame]))

(defn approach
  [params]
  [component/approach params])

(util/universal-set-loaded! :approach)
