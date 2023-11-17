import Header from './header';
import Info from './info';
import Map from './map';
import Footer from './footer';

const Main = () => {
    return (
        <div className="App">
          <Header/>
          <Info/>
          <Map/>
          <Footer/>
        </div>
    );
}

export default Main;