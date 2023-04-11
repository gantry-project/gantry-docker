import React, { useState, useEffect } from "react";
import styled from "@emotion/styled";
import axios from "axios";

const ApplicationDetail = () => {
  const [container, setContainer] = useState({});

  const getContainers = async () => {
    try {
      const res = await axios.get("http://localhost:8080/api/v1/applications/9000001");
      console.log("rescontainer", res); // 값이 없음
    } catch (err) {
      console.error(err);
    }
  };
  getContainers();
  return (
    <Container>
      <DetailItemsContainer>
        <DetailContainer>
          <RightContainer>
            <Logo>로고 이미지</Logo>
          </RightContainer>
          <LeftWrapper>
            <TopItem>
              <Title>Rstudio</Title>
              <LaunchBtn>LAUNCH</LaunchBtn>
            </TopItem>
            <BottomItem>설명</BottomItem>
          </LeftWrapper>
        </DetailContainer>
        <SnapchatWrapper>
          <Title>Snashots</Title>
          <SliderWrapp> 그림 들어갈 곳 </SliderWrapp>
        </SnapchatWrapper>
        <ConfigWrapper>
          <Title>Configuration</Title>
          <ConfigText></ConfigText>
        </ConfigWrapper>
      </DetailItemsContainer>
    </Container>
  );
};

export default ApplicationDetail;

const Container = styled.div`
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
  position: relative;
`;
const DetailItemsContainer = styled.div`
  width: 1320px;
  height: 600px;

  position: absolute;
  top: 40px;
  left: 80px;
`;
const DetailContainer = styled.div`
  height: 35%;
  display: flex;
`;
const RightContainer = styled.div`
  width: 25%;
  height: 100%;
`;
const Logo = styled.div`
  width: 250px;
  height: 200px;
  background-color: gray;
`;
const LeftWrapper = styled.div`
  width: 75%;
`;
const Title = styled.h1`
  font-size: 20px;
  padding-bottom: 5px;
`;
const LaunchBtn = styled.button`
  width: 110px;
  height: 28px;
  border-radius: 10px;

  color: white;
  background-color: skyblue;
  border: none;
  padding: 0;
  margin: 0;
`;
const TopItem = styled.div`
  display: flex;
  justify-content: space-between;
`;
const BottomItem = styled.div``;
const SnapchatWrapper = styled.div`
  height: 30%;
`;
const SliderWrapp = styled.div`
  width: 100%;
  height: 140px;
  background-color: gray;
`;
const ConfigWrapper = styled.div`
  height: 40%;
`;
const ConfigText = styled.div`
  background-color: gray;
  width: 100%;
  height: 160px;
`;
