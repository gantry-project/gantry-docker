import React, { FC, ReactNode } from "react";
import styled from "@emotion/styled";

//components
import Navbar from "./Navbar";
import Footer from "./Footer";

interface LayoutProps {
  children: ReactNode;
}

const Layout: FC<LayoutProps> = ({ children }) => {
  return (
    <Container>
      <Navbar />
      {children}
      <Footer />
    </Container>
  );
};

export default Layout;

const Container = styled.div``;
