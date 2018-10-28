/*    ==Scripting Parameters==

    Source Server Version : SQL Server 2017 (14.0.1000)
    Source Database Engine Edition : Microsoft SQL Server Express Edition
    Source Database Engine Type : Standalone SQL Server

    Target Server Version : SQL Server 2017
    Target Database Engine Edition : Microsoft SQL Server Express Edition
    Target Database Engine Type : Standalone SQL Server
*/

USE [QLSach]
GO

/****** Object:  StoredProcedure [dbo].[CapNhatChiTietHoaDon]    Script Date: 10/28/2018 10:40:17 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		Phuc
-- Create date: 
-- Description:	
-- =============================================
CREATE PROCEDURE [dbo].[CapNhatChiTietHoaDon] 
	-- Add the parameters for the stored procedure here
	@pMaKhachHang int = 0, 
	@pMaSach nvarchar(50) = '',
	@pSoLuongMua int = 0
AS
BEGIN
	DECLARE @pMaHoaDon int
	SELECT @pMaHoaDon = MaHoaDon FROM HoaDon WHERE MaKhachHang = @pMaKhachHang AND DaThanhToan = 'FALSE'

	IF(EXISTS(SELECT MaSach FROM ChiTietHoaDon WHERE MaSach = @pMaSach))
	BEGIN
		UPDATE ChiTietHoaDon
		SET SoLuongMua = SoLuongMua + @pSoLuongMua
		WHERE MaSach = @pMaSach
	END
	ELSE BEGIN
		INSERT INTO ChiTietHoaDon(MaSach, SoLuongMua, MaHoaDon)
		VALUES(@pMaSach, @pSoLuongMua, @pMaHoaDon)
	END
END
GO

/*    ==Scripting Parameters==

    Source Server Version : SQL Server 2017 (14.0.1000)
    Source Database Engine Edition : Microsoft SQL Server Express Edition
    Source Database Engine Type : Standalone SQL Server

    Target Server Version : SQL Server 2017
    Target Database Engine Edition : Microsoft SQL Server Express Edition
    Target Database Engine Type : Standalone SQL Server
*/

USE [QLSach]
GO

/****** Object:  UserDefinedFunction [dbo].[GetBookOfPage]    Script Date: 10/28/2018 10:40:59 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		
-- Create date: 
-- Description:	
-- =============================================
CREATE FUNCTION [dbo].[GetBookOfPage]
(	
	-- Add the parameters for the function here
	@pageNumber int,
	@bookPerPage int
	 
)
RETURNS TABLE 
AS
RETURN 
(
	SELECT TOP (@pageNumber * @bookPerPage) * FROM sach
	EXCEPT (SELECT TOP ((@pageNumber - 1) * @bookPerPage) * FROM sach)
)
GO

/*    ==Scripting Parameters==

    Source Server Version : SQL Server 2017 (14.0.1000)
    Source Database Engine Edition : Microsoft SQL Server Express Edition
    Source Database Engine Type : Standalone SQL Server

    Target Server Version : SQL Server 2017
    Target Database Engine Edition : Microsoft SQL Server Express Edition
    Target Database Engine Type : Standalone SQL Server
*/

USE [QLSach]
GO

/****** Object:  UserDefinedFunction [dbo].[GetBookOfPageByCategory]    Script Date: 10/28/2018 10:41:17 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

-- =============================================
-- Author:		
-- Create date: 
-- Description:	
-- =============================================
CREATE FUNCTION [dbo].[GetBookOfPageByCategory]
(	
	-- Add the parameters for the function here
	@categoryId nvarchar(50),
	@pageNumber int,
	@bookPerPage int
	 
)
RETURNS TABLE 
AS
RETURN 
(
	SELECT TOP (@pageNumber * @bookPerPage) * FROM sach WHERE MaLoai = @categoryId
	EXCEPT (SELECT TOP ((@pageNumber - 1) * @bookPerPage) * FROM sach WHERE MaLoai = @categoryId)
)
GO




