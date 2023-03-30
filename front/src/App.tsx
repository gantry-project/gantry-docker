import React from "react";
import { Route, Routes } from "react-router-dom";

//component
import Home from "./pages/Home";
import Layout from "./components/Layout";
import DockerList from "./pages/DockerList";
import UserCart from "./pages/UserCart";
import UserProfie from "./pages/User";
import ContainerDetail from "./pages/ContainerDetail";

function App() {
  return (
    <div>
      <Layout>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/dockerList" element={<DockerList />} />
          <Route path="/userAppCart" element={<UserCart />} />
          <Route path="/containerDetail" element={<ContainerDetail />} />
          <Route path="/user" element={<UserProfie />}></Route>
        </Routes>
      </Layout>
    </div>
  );
}

export default App;
