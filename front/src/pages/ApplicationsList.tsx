import React from "react";
import styled from "@emotion/styled";

//component
import DockerListHeader from "../components/ApplicationList/ApplicationListHeader";
import DockerCat from "../components/ApplicationList/ApplicationCat";
import { dummyData } from "../dummyData";

const ApplicationsList = () => {
  return (
    <Container>
      <DockerListHeader />
      <Wrapper>
        <DockerCat state={true} datas={dummyData} />
      </Wrapper>
    </Container>
  );
};

export default ApplicationsList;

const Container = styled.div`
  height: 100%;
  width: 100%;
  background-color: whitesmoke;
  padding: 10px;
`;
const Wrapper = styled.div`
  padding: 50px;
  padding: 20px;
  display: flex;
  flex-direction: column;
`;
