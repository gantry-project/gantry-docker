import React, { useCallback } from "react";
import styled from "@emotion/styled";
import { dummyData } from "../dummyData";
import { useNavigate } from "react-router-dom";

//components
import DockerCat from "../components/DockerList/DockerCat";

const UserCart = () => {
  const navigate = useNavigate();
  const onClickHandlerLists = useCallback(() => {
    return navigate(`/dockerList`);
  }, []);

  return (
    <Container>
      <Wrapper>
        <TopWrapper>
          <LeftIcon onClick={onClickHandlerLists}>go back lists</LeftIcon>
          <RightIcon>환경설정 완료</RightIcon>
        </TopWrapper>
        <BottmWrapper>
          <input placeholder="이름을 적어주세요" />
          <DockerCat state={false} datas={dummyData} />
        </BottmWrapper>
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
const TopWrapper = styled.div`
  height: 50px;

  display: flex;
  justify-content: space-between;
`;
const LeftIcon = styled.button``;
const RightIcon = styled.button``;

const BottmWrapper = styled.div`
  margin: 15px 0px;
`;
