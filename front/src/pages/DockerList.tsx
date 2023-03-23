import React from "react";
import styled from "styled-components";

//component
import DockerListHeader from "../components/DockerList/DockerListHeader";
import DockerCat from "../components/DockerList/DockerCat";

const dummyData = [
  {
    title: "create a new Kubernetes cluster",
    items: ["existing nodes"],
  },
  {
    title: "with rke and new nodes in an infastructure provider",
    items: ["amazon ec2", "azure", "digitalOcean", "Linode", "vSphere"],
  },
];

const DockerList = () => {
  return (
    <Container>
      <DockerListHeader />
      <Wrapper>
        <DockerCat datas={dummyData} />
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
