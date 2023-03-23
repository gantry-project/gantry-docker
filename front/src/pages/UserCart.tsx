import React from "react";
import styled from "styled-components";
import { dummyData } from "../dummyData";

//components
import DockerCat from "../components/DockerList/DockerCat";

const UserCart = () => {
  return (
    <Container>
      <Wrapper>
        <DockerCat state={false} datas={dummyData} />
      </Wrapper>
    </Container>
  );
};

export default UserCart;

const Container = styled.div`
  background-color: whitesmoke;
  width: 100%;
  height: 100vh;
`;

const Wrapper = styled.div`
  padding: 50px;
`;
