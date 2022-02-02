/**
 * @generated SignedSource<<b5ad7258716ef0c6bcbce012f86fd58a>>
 * @flow
 * @lightSyntaxTransform
 * @nogrep
 */

/* eslint-disable */

'use strict';

/*::
import type { ConcreteRequest, Query } from 'relay-runtime';
export type AppUserNamesQuery$variables = {||};
export type AppUserNamesQueryVariables = AppUserNamesQuery$variables;
export type AppUserNamesQuery$data = {|
  +Users: ?$ReadOnlyArray<{|
    +name: ?string,
  |}>,
|};
export type AppUserNamesQueryResponse = AppUserNamesQuery$data;
export type AppUserNamesQuery = {|
  variables: AppUserNamesQueryVariables,
  response: AppUserNamesQuery$data,
|};
*/

var node/*: ConcreteRequest*/ = (function(){
var v0 = {
  "alias": null,
  "args": null,
  "kind": "ScalarField",
  "name": "name",
  "storageKey": null
};
return {
  "fragment": {
    "argumentDefinitions": [],
    "kind": "Fragment",
    "metadata": null,
    "name": "AppUserNamesQuery",
    "selections": [
      {
        "alias": null,
        "args": null,
        "concreteType": "User",
        "kind": "LinkedField",
        "name": "Users",
        "plural": true,
        "selections": [
          (v0/*: any*/)
        ],
        "storageKey": null
      }
    ],
    "type": "Query",
    "abstractKey": null
  },
  "kind": "Request",
  "operation": {
    "argumentDefinitions": [],
    "kind": "Operation",
    "name": "AppUserNamesQuery",
    "selections": [
      {
        "alias": null,
        "args": null,
        "concreteType": "User",
        "kind": "LinkedField",
        "name": "Users",
        "plural": true,
        "selections": [
          (v0/*: any*/),
          {
            "alias": null,
            "args": null,
            "kind": "ScalarField",
            "name": "id",
            "storageKey": null
          }
        ],
        "storageKey": null
      }
    ]
  },
  "params": {
    "cacheID": "88742b832cd8dea2155673b6d927b481",
    "id": null,
    "metadata": {},
    "name": "AppUserNamesQuery",
    "operationKind": "query",
    "text": "query AppUserNamesQuery {\n  Users {\n    name\n    id\n  }\n}\n"
  }
};
})();

(node/*: any*/).hash = "5f77b587a1c94d1b68fae932e2976068";

module.exports = ((node/*: any*/)/*: Query<
  AppUserNamesQuery$variables,
  AppUserNamesQuery$data,
>*/);
