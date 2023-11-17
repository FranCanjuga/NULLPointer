import './App.css';
import { Routes, Route } from 'react-router-dom';

import Main from './components/main'
import SignIn from './components/signin';
import Register from './components/register';

function App() {
  return (
      <Routes>
        <Route path="/" element={<Main/>} />
        <Route path="/prijava" element={<SignIn/>} />
        <Route path='/registracija' element={<Register/>}/>
     </Routes>
  );
}

export default App;
