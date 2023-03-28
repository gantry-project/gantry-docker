import React from "react";
import styled from "@emotion/styled";

//component
import DockerListHeader from "../components/DockerList/DockerListHeader";
import DockerCat from "../components/DockerList/DockerCat";
import { dummyData } from "../dummyData";

const DockerList = () => {
  return (
    <Container>
      <DockerListHeader />
      <Wrapper>
        <DockerCat state={true} datas={dummyData} />
      </Wrapper>
    </Container>
  );
};

export default DockerList;

const Container = styled.div`
  height: 100vh;
  background-color: whitesmoke;
  padding: 10px;
`;
const Wrapper = styled.div`
  padding: 50px;
`;
