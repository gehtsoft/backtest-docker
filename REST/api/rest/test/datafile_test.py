import json, requests, sys, os.path
import rest_conf as conf
import datafile_rest as datafiles
import unittest


class DataFileTest(unittest.TestCase):

    def test_datafiles(self):
        file = conf.DATA_ADD
        resp = datafiles.add_datafile(file)
        self.assertEqual(resp.status_code, 200, "Invalid response code {0}, {1}".format(resp.status_code, resp.content))

        found = datafiles.find_datafile(file)
        self.assertTrue(found, "Added datafile not found")

        resp_del = datafiles.delete_datafile(file)
        self.assertEqual(resp_del.status_code, 200, "Invalid response delete code {0}, {1}".format(resp_del.status_code, resp_del.content))

        found = datafiles.find_datafile(file)
        self.assertTrue(not found, "Datafile was not deleted")





if __name__ == "__main__":
    unittest.main()

