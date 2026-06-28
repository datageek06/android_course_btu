import { useEffect, useState } from 'react'
import './App.css'
import axios from 'axios';

function App() {
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(true) // სანამ არ მივიღებთ იუსერებს მანამდე, Loading-ს აჩვენებს

  useEffect(() => {
    axios.get('https://jsonplaceholder.typicode.com/users')
      .then(response => {
        setUsers(response.data)
        setLoading(false)
      })
      .catch(error => {
        console.error("შეცდომა:", error);
        setLoading(false)
      });
  }, []);

  if (loading) {
    return <div>Loading Users....</div>;
  }

  return (
    <div className="container">
        {users.map(user => (
          <div key={user.id} className="user">
            <h2>{user.name}</h2>
            <h3>{user.email}</h3>
          </div>
        ))}
    </div>
  )
}

export default App
