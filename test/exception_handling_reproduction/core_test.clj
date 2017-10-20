(ns exception-handling-reproduction.core-test
	(:require [clojure.test :refer :all]
						[midje.sweet :refer :all]
						[exception-handling-reproduction.core :refer :all]
						[exception-handling-reproduction.utils :as u]
						[exception-handling-reproduction.test-utils :as tu]
						[clj-http.client :as http]
						[com.stuartsierra.component :as component]))

(deftest test-core-system
	(facts "about run"
				 (let [dev-config (u/load-dev-config)
							 test-config (merge {:http-port      (u/get-free-port)
																	 :mgmt-http-port (u/get-free-port)}
																	dev-config)
							 system (run test-config)
							 port (-> system :http :configuration :port)]
					 (try
						 (tu/wipe-db (:db system))
						 (facts "Returns 200 when form-params match the swagger def."
										(http/put (str "http://localhost:" port "/memories/foo") {:content-type     :json
																																							:throw-exceptions false
																																							:form-params      {:text ["foo" "bar"]}})
										=> (contains {:status 200}))
						 (tu/wipe-db (:db system))
						 (facts "For string value, Throws validation error that isn't a HystrixBadRequestException"
										(let [response (http/put (str "http://localhost:" port "/memories/foo") {:content-type     :json
																																														 :throw-exceptions false
																																														 :form-params      {:text "bar"}})]
											response
											=> (contains {:status 400})
											(empty?
													 (re-matches
														 (re-pattern "HystrixBadRequestException")
														 (:body response)))
											=> truthy
											(empty?
													 (re-matches
														 (re-pattern "IllegalArgumentException: Don't know how to create ISeq from:")
														 (:body response)))
											=> truthy))
						 (tu/wipe-db (:db system))
						 (facts "For integer value, Throws validation error that isn't a HystrixBadRequestException"
										(let [response (http/put (str "http://localhost:" port "/memories/foo") {:content-type     :json
																																														 :throw-exceptions false
																																														 :form-params      {:text 1234}})]
											response
											=> (contains {:status 400})
											(empty?
													 (re-matches
														 (re-pattern "HystrixBadRequestException")
														 (:body response)))
											=> truthy
											(empty?
													 (re-matches
														 (re-pattern "IllegalArgumentException: Don't know how to create ISeq from:")
														 (:body response)))
											=> truthy))
						 (tu/wipe-db (:db system))
						 (facts "For array with integer items, throws validation error that array items are not strings"
										(let [response (http/put (str "http://localhost:" port "/memories/foo") {:content-type     :json
																																														 :throw-exceptions false
																																														 :form-params      {:text [1234]}})]
											response
											=> (contains {:status 400})
											(empty?
													 (re-matches
														 (re-pattern "HystrixBadRequestException")
														 (:body response)))
											=> truthy
											(empty?
													 (re-matches
														 (re-pattern "IllegalArgumentException: Don't know how to create ISeq from:")
														 (:body response)))
											=> truthy))
						 (tu/wipe-db (:db system))
						 (finally
							 (component/stop system))))))
