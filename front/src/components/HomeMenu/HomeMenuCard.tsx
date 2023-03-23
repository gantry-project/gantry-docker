import React, { useCallback } from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";

const HomeMenuCard = () => {
  const navigate = useNavigate();

  const onClickHandler = useCallback(() => {
    navigate(`/dockerList`);
  }, []);

  return (
    <Container onClick={onClickHandler}>
      <Title>DockerList</Title>
    </Container>
  );
};

export default HomeMenuCard;

const Container = styled.div`
  width: 250px;
  height: 200px;
  border: 1px solid black;
  border-radius: 10px;
  cursor: pointer;
`;
const Title = styled.h1`
  font-size: 50px;
`;
