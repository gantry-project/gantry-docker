import React from "react";

//component
import Home from "./pages/Home/Home";
import Navbar from "./components/Navbar";
import MainMenu from "./components/HomeMenu/HomeMenu";
import Footer from "./components/Footer";

function App() {
  return (
    <div>
      <Navbar />
      <Home />
      <MainMenu />
      <Footer />
    </div>
  );
}

export default App;
