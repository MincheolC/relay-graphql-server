(ns graphql.custom-scalar)

(def schema {:datetime-parser     identity
             :datetime-serializer identity})
