(ns tutorial-client.simulated.start
  (:require [io.pedestal.app.protocols :as p]
            [io.pedestal.app :as app]
            [io.pedestal.app.render.push.handlers.automatic :as d]
            [tutorial-client.start :as start]
            ;; This needs to be included somewhere in order for the
            ;; tools to work.
            [io.pedestal.app-tools.tooling :as tooling]
            [tutorial-client.rendering :as rendering]
            [tutorial-client.simulated.services :as services]
            [goog.Uri :as guri]))

(defn ^:export main []
  (let [uri (goog.Uri. (.toString (.-location js/document)))
        renderer (.getParameterValue uri "renderer")
        render-config (if (= renderer "auto")
                        d/data-renderer-config
                        (rendering/render-config))
        app (start/create-app render-config)
        services (services/->MockServices (:app app))]
    (app/consume-effects (:app app) services/services-fn)
    (p/start services)
    app))
