SELECT * FROM (
  SELECT 
    F12021.t_participant_data_2021.m_num_active_cars
  FROM F12021.t_packet_header_2021
  JOIN F12021.t_participant_data_2021 ON F12021.t_packet_header_2021.id = F12021.t_participant_data_2021.m_packet_id
  WHERE F12021.t_packet_header_2021.m_session_uid = ?
  ORDER BY F12021.t_packet_header_2021.id DESC
)