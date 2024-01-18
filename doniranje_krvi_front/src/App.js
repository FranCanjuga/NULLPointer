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
import SecretPage from  './components/easterEgg';
import Features from './components/features';
import Privacy from './components/privacy';


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
        <Route path ='/secretPage' element={<SecretPage/>}/>
        <Route path ='/features' element={<Features/>}/>
        <Route path ='/privacy' element={<Privacy/>}/>
     </Routes>
  );
}

export default App;
