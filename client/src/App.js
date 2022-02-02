import React from 'react';
import './App.css';
import graphql from 'babel-plugin-relay/macro';
import {
  RelayEnvironmentProvider,
  loadQuery,
  usePreloadedQuery,
} from 'react-relay/hooks';
import RelayEnvironment from './RelayEnvironment';

const { Suspense } = React;

const getUserNamesQuery = graphql`
  query AppUserNamesQuery {
    Users {
      name
    }
  }
`

// Immediately load the query as our app starts. For a real app, we'd move this
// into our routing configuration, preloading data as we transition to new routes.
const preloadedQuery = loadQuery(RelayEnvironment, getUserNamesQuery, {
  /* query variables */
});

function App(props) {
  const data = usePreloadedQuery(getUserNamesQuery, props.preloadedQuery);

  return (
    <div className="App">
      <header className="App-header">
        <p>{data.Users.map(v => v.name)}</p>
      </header>
    </div>
  );
}

function AppRoot(props) {
  return (
    <RelayEnvironmentProvider environment={RelayEnvironment}>
      <Suspense fallback={'Loading...'}>
        <App preloadedQuery={preloadedQuery} />
      </Suspense>
    </RelayEnvironmentProvider>
  );
}

export default AppRoot;
