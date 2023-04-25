import React from "react";
import styled from "@emotion/styled";

import ApplicationListHeader from "components/ApplicationList/ApplicationListHeader";
import ApplicationCat from "components/ApplicationList/ApplicationCat";

const ApplicationsList = () => {
  return (
    <Container>
      <ApplicationListHeader />
      <Wrapper>
        <ApplicationCat />
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
