import React from "react";
import styled from "@emotion/styled";

const Footer = () => {
  return (
    <Container>
      <BottomWrapper>
        <Logo>GANTRY</Logo>
        <ItemWrapper>
          <ItemUl>products</ItemUl>
          <ItemLi>granty</ItemLi>
          <ItemLi>harvester</ItemLi>
          <ItemLi>longhorn</ItemLi>
          <ItemLi>granty</ItemLi>
        </ItemWrapper>
        <ItemWrapper>
          <ItemUl>learn</ItemUl>
          <ItemLi>learn the basics</ItemLi>
          <ItemLi>grow your skills</ItemLi>
          <ItemLi>get Certified</ItemLi>
        </ItemWrapper>
        <ItemWrapper>
          <ItemUl>coummunity</ItemUl>
          <ItemLi>slack</ItemLi>
          <ItemLi>github</ItemLi>
          <ItemLi>blogs</ItemLi>
        </ItemWrapper>
        <ItemWrapper>
          <ItemUl>number</ItemUl>
          <ItemLi>about granty</ItemLi>
          <ItemLi>partners</ItemLi>
          <ItemLi>events</ItemLi>
        </ItemWrapper>
      </BottomWrapper>
    </Container>
  );
};

export default Footer;
const Container = styled.div`
  width: 100%;
  display: flex;
`;

const Logo = styled.h1`
  font-size: 50px;
`;

const BottomWrapper = styled.div`
  padding: 70px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 250px;
`;
const ItemUl = styled.ul`
  font-weight: bold;
  margin: 5px;
  padding-bottom: 5px;
`;
const ItemWrapper = styled.div`
  width: 150px;
  height: 200px;
  padding: 30px;
  display: flex;
  align-items: center;
  flex-direction: column;
`;
const ItemLi = styled.li`
  display: inline-block;
  margin: 10px;
`;