import React from "react";
import { Route, Routes } from "react-router-dom";

//component
import Home from "./pages/Home";
import Layout from "./components/Layout";
import DockerList from "./pages/DockerList";

function App() {
  return (
    <div>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/dockerList" element={<DockerList />} />
        </Routes>
      </Layout>
    </div>
  );
}

export default App;
