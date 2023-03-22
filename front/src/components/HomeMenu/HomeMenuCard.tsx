import React, { useCallback } from "react";
import styled from "styled-components";

const HomeMenuCard = () => {
  const onClickHandler = useCallback(() => {}, []);
  return (
    <Container>
      <Title onClick={onClickHandler}>DockerList</Title>
    </Container>
  );
};

export default HomeMenuCard;

const Container = styled.div`
  width: 250px;
  height: 200px;
  border: 1px solid black;
  border-radius: 10px;
`;

const Title = styled.h1`
  font-size: 50px;
`;
