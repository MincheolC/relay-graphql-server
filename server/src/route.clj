(ns route
  (:require [graphql.route :as gr]
            [muuntaja.core :as m]
            [reitit.ring :refer [router ring-handler]]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.parameters :as params]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.cors :as cors]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.util.response :refer [response]]
            [ring.util.http-response :as hr]))

(defn wrap-cors [handler]
  (cors/wrap-cors handler
                  :access-control-allow-origin #".*"
                  :access-control-allow-methods [:get :post :put :delete :patch]))

(defn app
  [context]
  (ring-handler
    (router
      [""
       ["/api" {:get {:no-doc    true
                      :summary   "health check"
                      :responses {200 {:body {:message string?}}}
                      :handler   (fn [_] (hr/ok {:message "welcome to api server"}))}}]
       ["/graphql" {:post {:summary    "graphql handler"
                           :responses  {200 {:body any?}}
                           :handler    (gr/handler context)}}]]
      ;; router data affecting all routes
      {:data {:muuntaja   m/instance
              :middleware [params/parameters-middleware
                           muuntaja/format-middleware]}})
    nil
    {:middleware [wrap-reload
                  wrap-cors
                  wrap-content-type
                  wrap-not-modified]}))
