import './css/App.css';
import { Routes, Route } from 'react-router-dom';
import 'boxicons'

import Main from './components/main'
import SignIn from './components/signin';
import Register from './components/register';
import User from './components/user';
import Contact from './components/contact';
import UserData from './components/userData';
import History from  './components/history';

function App() {
  return (
      <Routes>
        <Route path="/" element={<Main/>} />
        <Route path="/prijava" element={<SignIn/>} />
        <Route path='/registracija' element={<Register/>}/>
        <Route path='/user' element={<User/>}/>
        <Route path='/contact' element={<Contact/>}/>
        <Route path ='/userData' element={<UserData/>}/>
        <Route path ='/povijestDoniranja' element={<History/>}/>
     </Routes>
  );
}

export default App;
