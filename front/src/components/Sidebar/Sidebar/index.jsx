import React from 'react'
import { Container, Content } from './styles'
import { 
  FaTimes, 
  FaHome, 
  FaEnvelope, 
  FaRegSun, 
  FaUserAlt, 
  FaIdCardAlt, 
  FaRegFileAlt,
  FaRegCalendarAlt,
  FaChartBar
} from 'react-icons/fa'

import SidebarItem from '../SidebarItem'

const Sidebar = ({ active }) => {

  const closeSidebar = () => {
    active(false)
  }

  return (
    <Container sidebar={active}>
      <FaTimes onClick={closeSidebar} />  
      <Content>
        <SidebarItem Icon={FaHome} Text="메인 메뉴" />
        <SidebarItem Icon={FaChartBar} Text="통계표" />
        <SidebarItem Icon={FaUserAlt} Text="개인" />
        <SidebarItem Icon={FaEnvelope} Text="메일" />
        <SidebarItem Icon={FaRegCalendarAlt} Text="캘린더" />
        <SidebarItem Icon={FaIdCardAlt} Text="사용자" />
        <SidebarItem Icon={FaRegFileAlt} Text="리포트" />
        <SidebarItem Icon={FaRegSun} Text="설정" />
      </Content>
    </Container>
  )
}

export default Sidebar