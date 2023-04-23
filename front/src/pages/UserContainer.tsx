import React from "react";
import styled from "@emotion/styled";

import ContainerCard from "../components/UseContainer/ContainerCard";

const UserContainer = () => {
  return (
    <Container>
      <Wrapper>
        <TopWrapper>
          <Title>나의 컨테이너</Title>
        </TopWrapper>
        <BottomWrapper>
          <ContainerCard />
          <ContainerCard />
          <ContainerCard /> <ContainerCard />
          <ContainerCard />
          <ContainerCard />
          <ContainerCard />
        </BottomWrapper>
      </Wrapper>
    </Container>
  );
};

export default UserContainer;

const Container = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Wrapper = styled.div`
  width: 900px;
  height: 600px;
  border: 1px solid black;
`;
const Title = styled.h1`
  font-size: 60px;
`;

const TopWrapper = styled.div`
  width: 100%;
  height: 20%;
  display: flex;
  justify-content: center;
`;

const BottomWrapper = styled.div`
  width: 100%;
  height: 80%;
  padding: 50px;
  display: flex;
  flex-wrap: wrap;
`;
