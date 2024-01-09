import './css/App.css';
import { Routes, Route } from 'react-router-dom';
import 'boxicons'

import Main from './components/main'
import SignIn from './components/signin';
import Register from './components/register';
import User from './components/user';

function App() {
  return (
      <Routes>
        <Route path="/" element={<Main/>} />
        <Route path="/prijava" element={<SignIn/>} />
        <Route path='/registracija' element={<Register/>}/>
        <Route path='/user' element={<User/>}/>
     </Routes>
  );
}

export default App;
