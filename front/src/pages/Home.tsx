import React from "react";
import styled from "@emotion/styled";

//component
import MainMenu from "../components/HomeMenu/HomeMenu";


const Home = () => {
  return (
    <>
      <Container>
        <Title>GANTRY</Title>
      </Container>
      <MainMenu />
    </>
  );
};

export default Home;
const Container = styled.div`
  width: 100%;
  height: 100vh;
  margin: 50px 50px 0px 0px;
`;
const Title = styled.h1`
  font-size: 50px;
`;