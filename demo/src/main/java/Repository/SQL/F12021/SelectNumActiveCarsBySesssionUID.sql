SELECT * FROM (
  SELECT 
    t_participant_data_2021.m_num_active_cars
  FROM t_packet_header_2021
  JOIN t_participant_data_2021 ON t_packet_header_2021.id = t_participant_data_2021.m_packet_id
  WHERE t_packet_header_2021.m_session_uid = ?
  ORDER BY t_packet_header_2021.id DESC
)