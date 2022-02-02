import React from 'react';
import './App.css';
import fetchGraphQL from './fetchGraphQL';

const { useState, useEffect } = React;

function App() {
  const [userNames, setUserNames] = useState(null);

  useEffect(() => {
    let isMounted = true;
    fetchGraphQL(`
      query GetUsersNameQuery {
        Users {
          name
        }
      }
    `).then(response => {
      // Avoid updating state if the component unmounted before the fetch completes
      if (!isMounted) {
        return;
      }
      const data = response.data;
      setUserNames(data.Users.map(v => v.name))
    }).catch(error => {
      console.error(error);
    });

    return () => {
      isMounted = false;
    };
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <p>
          {userNames != null ? `User Names: ${userNames}` : "Loading"}
        </p>
      </header>
    </div>
  );
}

export default App;
