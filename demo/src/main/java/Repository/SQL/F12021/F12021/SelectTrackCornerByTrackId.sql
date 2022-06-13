SELECT
  f12021.t_track_corner_2021.id,
  f12021.t_track_corner_2021.m_track_id,
  f12021.t_track_corner_2021.m_corner,
  f12021.t_track_corner_2021.m_apex_1,
  f12021.t_track_corner_2021.m_apex_2,
  f12021.t_track_corner_2021.m_apex_3,
  f12021.t_track_corner_2021.m_x,
  f12021.t_track_corner_2021.m_y
FROM f12021.t_track_corner_2021
WHERE f12021.t_track_corner_2021.m_track_id = ?