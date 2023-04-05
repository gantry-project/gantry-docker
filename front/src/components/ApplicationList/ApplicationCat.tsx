import React, { FC, useCallback, useState, useEffect } from "react";
import styled from "@emotion/styled";
import axios from "axios";

//compoments
interface Containers {
  datas: {
    id: string;
    title: string;
    img: string;
    desc: string;
    logo: string;
  }[];
}
interface Props {
  datas: {
    id: string;
    title: string;
    img: string;
    desc: string;
    logo: string;
  }[];

  state: boolean;
}

const ApplicationCat: FC<Props> = ({ datas, state }) => {
  const [isHovered, setIsHovered] = useState(state);
  const [containers, setContainers] = useState<Containers>();

  /**유저가 고른 데이터 */
  useEffect(() => {
    const getContainers = async () => {
      try {
        const res = await axios.get("http://localhost:8080/applications");
        console.log("res", res); // 값이 없음
      } catch (err) {
        console.error(err);
      }
    };
    getContainers();
  }, []);

  const onClickHandler = useCallback((title: string, item: string) => {
    console.log("title", title);
    console.log("item", item);
  }, []);

  return (
    <>
      <Category>database</Category>
      <Container>
        {containers?.datas.map((item) => {
          return (
            <>
              <ItemWrapper>
                <ImageWrapper>{item.img}</ImageWrapper>
                <ItemBottom>
                  <ItemLogoWrapper>
                    <Logo>LO</Logo>
                  </ItemLogoWrapper>
                  <ItemBottomRight>
                    <ItemTitle>{item.title}</ItemTitle>
                    <ItemDesc>{item.desc}</ItemDesc>
                  </ItemBottomRight>
                </ItemBottom>
              </ItemWrapper>
            </>
          );
        })}
      </Container>
    </>
  );
};

export default ApplicationCat;

const Container = styled.div`
  /* flex-direction: column; */
  width: 100%;
  display: flex;
  flex-wrap: wrap;
`;
const Category = styled.h1`
  border: 1px solid black;
  width: 90px;
  height: 25px;
  text-align: center;
  border-radius: 20px;
`;

const ItemWrapper = styled.div`
  width: 240px;
  height: 300px;
  padding: 30px;
  cursor: pointer;
`;
const ImageWrapper = styled.div`
  width: 100%;
  height: 73%;
  margin-bottom: 5px;
  background-color: gray;
`;

const ItemBottom = styled.div`
  width: 100%;
  height: 25%;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const ItemLogoWrapper = styled.div`
  height: 100%;

  width: 20%;
`;
const Logo = styled.div`
  border: 1px solid black;
  border-radius: 50%;
  margin: 4px;
  height: 30px;
  width: 30px;
`;
const ItemBottomRight = styled.div`
  height: 100%;
  width: 80%;

  margin-top: 20px;
`;
const ItemTitle = styled.h1`
  font-weight: bold;
  margin-bottom: 15px;
`;
const ItemDesc = styled.div`
  font-size: 15px;
`;
