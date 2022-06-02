SELECT 
  m_player_name, 
  m_player_car_index,
  m_session_uid, 
  MIN(m_created_on)
FROM F12021.t_session_lookup_2021
GROUP BY m_session_uid, m_player_name
ORDER BY MIN(m_created_on) DESC