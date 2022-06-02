export interface Section {
  id: number;
  name: string;
  title: string;
  city: string;
  country: string;
  description: string;
  nameEng?: string;
  titleEng?: string;
  cityEng?: string;
  countryEng?: string;
  descriptionEng?: string;
  images: { id: number; image: any }[];
  file: string;
  isMobileDescriptionExtended: boolean;
  isMobileFormExtended: boolean;
}
