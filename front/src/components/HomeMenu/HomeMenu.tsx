import React from "react";
import styled from "styled-components";

//components
import HomeMenuCard from "./HomeMenuCard";

const HomeMenu = () => {
  return (
    <Container>
      <CardWrapper>
        <HomeMenuCard />
        <HomeMenuCard />
        <HomeMenuCard />
      </CardWrapper>
    </Container>
  );
};

export default HomeMenu;

const Container = styled.div`
  background-color: #327e7e;
  width: 100%;
  height: 500px;
  align-items: center;
  display: flex;
  justify-content: center;
`;
const CardWrapper = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 900px;
  height: 300px;
  padding: 80px;
`;
